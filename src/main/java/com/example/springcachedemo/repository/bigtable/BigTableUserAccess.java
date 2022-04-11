package com.example.springcachedemo.repository.bigtable;

import com.example.springcachedemo.service.UserAccess;
import com.example.springcachedemo.service.model.User;
import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.models.Row;
import com.google.cloud.bigtable.data.v2.models.RowMutation;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@ConditionalOnProperty(value = "storage.mode", havingValue = "bigtable")
@RequiredArgsConstructor
class BigTableUserAccess implements UserAccess {

    private static final String USERS_TABLE = "users";

    private static final String PERSONAL_INFO_FAMILY = "personal-info";
    private static final String NAME_COLUMN = "name";

    private final BigtableDataClient bigtableDataClient;

    @Override
    public Optional<User> findById(String id) {
        var row = bigtableDataClient.readRow(USERS_TABLE, id);
        if (row == null) {
            return Optional.empty();
        }
        var user = parseUser(id, row);
        return Optional.of(user);
    }

    @Override
    public User create(User user) {
        var userId = user.getName() + "-" + UUID.randomUUID();
        return doUpdate(userId, user);
    }

    @Override
    public User update(User user) {
        return doUpdate(user.getId(), user);
    }

    @Override
    public void deleteById(String id) {
        var deleteMutation = mutation(id).deleteRow();
        bigtableDataClient.mutateRow(deleteMutation);
    }

    private RowMutation mutation(String id) {
        return RowMutation.create(USERS_TABLE, id);
    }

    private User parseUser(String id, Row row) {
        var builder = User.builder()
                .id(id);
        row.getCells().forEach(cell -> {
            if (NAME_COLUMN.equals(cell.getQualifier().toStringUtf8())) {
                builder.name(cell.getValue().toStringUtf8());
            }
        });
        return builder.build();
    }

    private User doUpdate(String userId, User user) {
        var updateMutation = mutation(userId)
                .setCell(PERSONAL_INFO_FAMILY, NAME_COLUMN, user.getName());
        bigtableDataClient.mutateRow(updateMutation);
        user.setId(userId);
        return User.builder()
                .id(userId)
                .name(user.getName())
                .build();
    }
}
