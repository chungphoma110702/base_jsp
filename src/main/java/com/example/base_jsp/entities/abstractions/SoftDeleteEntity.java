package com.example.base_jsp.entities.abstractions;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class SoftDeleteEntity extends BaseEntity {

    @Column(name = "deleted_by")
    @Comment("Người xóa")
    private String deletedBy;

    @Column(name = "deleted_at")
    @Comment("Thời gian xóa")
    private LocalDateTime deletedAt;
}
