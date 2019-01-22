package com.github.sigma.blog.data;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

import static com.github.sigma.blog.data.Post.FIND_ALL;
import static com.github.sigma.blog.data.Post.FIND_BY_ID;
import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table
@NamedQueries({
        @NamedQuery(name = FIND_BY_ID, query = "SELECT p FROM Post p WHERE p.id = :id"),
        // requires Comparable to be implemented:
        @NamedQuery(name = FIND_ALL, query = "SELECT p FROM Post p ORDER BY p.updatedAt DESC"),
})
public class Post implements Comparable<Post>/*, Formattable*/ {
/*
    @Override
    public void formatTo(Formatter formatter, int flags, int width, int precision) {
        if (id == null) formatter.format("Post {}");
        else formatter.format("Post {\"body\":\"" + body + "\"}");
    }
*/

    public static final String FIND_ALL = "Post.findAll";
    public static final String FIND_BY_ID = "Post.findById";

    @Id
    @GeneratedValue(generator = "UUID2")
    @GenericGenerator(name = "UUID2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column
    private String body;

    @Temporal(TIMESTAMP)
    @Column(name = "created")
    private Date createdAt;

    @Temporal(TIMESTAMP)
    @Column(name = "updated")
    private Date updatedAt;

    protected Post() {}

    public Post(final UUID id, final String body, final Date createdAt, final Date updatedAt) {
        Objects.requireNonNull(id, "id may not be null.");
        Objects.requireNonNull(body, "body may not be null.");
        Objects.requireNonNull(createdAt, "createdAt may not be null.");
        Objects.requireNonNull(updatedAt, "updatedAt may not be null.");
        this.id = id;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Post(final String body) {
        this.body = body;
    }

    @Override
    public int compareTo(final Post other) {
        return -this.updatedAt.compareTo(other.getCreatedAt());
    }
    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                "body='" + body + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @PrePersist
    public void onCreate() {
        final long time = new Date().getTime();
        createdAt = new Date(time);
        updatedAt = new Date(time);
    }

    @PreUpdate
    public void onMerge() {
        updatedAt = new Date();
    }

    public boolean isNew() {
        return id == null;
    }

    protected void setId(UUID id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public UUID getId() {
        return id;
    }
}
