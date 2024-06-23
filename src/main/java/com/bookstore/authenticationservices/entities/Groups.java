package com.bookstore.authenticationservices.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "groups")
@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Groups implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;
    @Column(name = "groupName")
    private String groupName;
    @Column(name = "groupDescription")
    private String groupDescription;
    @Column(name = "dateCreated")
    private Date dateCreated;

    @OneToMany(mappedBy = "groups")
    @JsonManagedReference
    private List<Permissions> permissions;

}
