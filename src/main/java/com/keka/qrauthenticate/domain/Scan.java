package com.keka.qrauthenticate.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_scans")
@EntityListeners(AuditingEntityListener.class)
public class Scan {
    @Id @GeneratedValue
    private UUID id;

    @ManyToOne
    private Item item;

    @Column(name = "scanned_at", nullable = false)
    private LocalDateTime scannedAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private  LocalDateTime updateAt;
}
