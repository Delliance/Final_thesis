package com.danielfinal.finalproject.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity(name = "Tag")
@Table(
        name = "tbl_tag"
)
public class Tag {

    @Id
    @SequenceGenerator(
            name = "tag_sequence",
            sequenceName = "tag_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tag_sequence"
    )
    @Column(
            name = "tag_id",
            updatable = false
    )
    private long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;
}
