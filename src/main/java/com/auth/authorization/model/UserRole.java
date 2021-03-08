package com.auth.authorization.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor

public class UserRole {
    public static final String GET_USER_BY_UNIQUE_ID = "UserRole.getUserByUniqueId";
    @Id
    @SequenceGenerator(
            name="user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    private String uniqueId;

    private String role;
}
