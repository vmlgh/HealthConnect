package com.healthconnect.platform.entity.common;

import com.healthconnect.platform.dto.common.NotificationDto;
import com.healthconnect.platform.entity.core.BaseEntity;
import com.healthconnect.platform.entity.core.User;

import javax.persistence.*;

@Entity
@Table(name = "Notification")
public class Notification extends BaseEntity {

    @Column(name = "TargetUrl")
    private String targetUrl;
    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;
    @Column(name = "IsExpired", columnDefinition = "boolean default 0")
    private boolean expired;
    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NotificationDto toNotificationDto(){
        NotificationDto dto = new NotificationDto();
        dto.setDescription(this.description);
        dto.setName(this.name);
        dto.setTargetUrl(this.targetUrl);
        dto.setId(this.getRecordId());
        return dto;
    }
}

