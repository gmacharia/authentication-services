package com.bookstore.authenticationservices.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Table(name = "permissions")
@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permissions implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int permissionId;
    @Column(name = "permissionScope")
    private String permissionScope;
    @Column(name = "permissionDescription")
    private String permissionDescription;
    @Column(name = "dateCreated")
    private Date dateCreated;
    @ManyToOne
    @JoinColumn(name = "groupId")
    @JsonBackReference
    private Groups groups;
}
