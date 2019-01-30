package com.github.sigma.blog.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

import static com.github.sigma.blog.domain.Post.*;
import static javax.persistence.TemporalType.TIMESTAMP;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
@NamedQueries({
        @NamedQuery(name = COUNT, query = "SELECT COUNT(p) FROM Post p WHERE p.id = :id"),
        @NamedQuery(name = FIND_BY_ID, query = "SELECT p FROM Post p WHERE p.id = :id"),
        // requires Comparable to be implemented:
        @NamedQuery(name = FIND_ALL, query = "SELECT p FROM Post p ORDER BY p.lastModifiedAt DESC"),
})
public class Post implements Comparable<Post> {

    public static final String FIND_ALL = "Post.findAll";
    public static final String FIND_BY_ID = "Post.findById";
    public static final String COUNT = "Post.count";

    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(generator = "UUID2")
    @GenericGenerator(name = "UUID2", strategy = "org.hibernate.id.UUIDGenerator")
    protected UUID id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String body;

    @Temporal(TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date lastModifiedAt;

    public Post(final String title, final String body) {
        this.title = title;
        this.body = body;
    }

    @Override
    public int compareTo(final Post other) {
        return -this.lastModifiedAt.compareTo(other.getLastModifiedAt());
    }

    @PrePersist
    public void onCreate() {
        final long time = new Date().getTime();
        lastModifiedAt = new Date(time);
    }

    @PreUpdate
    public void onMerge() {
        lastModifiedAt = new Date();
    }

    public boolean isNew() {
        return id == null;
    }
}
