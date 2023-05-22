package ru.pivovarov.AvatarCRUD.handler;

import ru.pivovarov.AvatarCRUD.entity.User;

public class UserStatusData {
    private int id;
    private User.Status status;
    private User.Status previousStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User.Status getStatus() {
        return status;
    }

    public void setStatus(User.Status status) {
        this.status = status;
    }

    public User.Status getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(User.Status previousStatus) {
        this.previousStatus = previousStatus;
    }

    @Override
    public String toString() {
        return "UserStatusData{" +
                "id=" + id +
                ", status=" + status +
                ", previousStatus=" + previousStatus +
                '}';
    }
}
