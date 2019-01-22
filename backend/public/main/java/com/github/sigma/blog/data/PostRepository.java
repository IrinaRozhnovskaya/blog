package com.github.sigma.blog.data;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.*;

import static com.github.sigma.blog.data.Post.FIND_ALL;
import static com.github.sigma.blog.data.Post.FIND_BY_ID;
import static java.util.Collections.unmodifiableList;

@Stateless
public class PostRepository {

    public static final Integer PAGE_SIZE = 5;

    @Inject
    EntityManager em;

    public Post save(final Post post) {
        if (post.isNew()) em.persist(post);
        else em.merge(post);
        return post;
    }

    public List<Post> getAll() {
        return unmodifiableList(em.createNamedQuery(FIND_ALL, Post.class)
                .setMaxResults(PAGE_SIZE)
                .getResultList());
    }

    public Post findById(UUID id) {
        final Iterator<Post> iterator = em.createNamedQuery(FIND_BY_ID, Post.class)
                .setParameter("id", id)
                .getResultList()
                .iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }

    public Post update(UUID id, String body) {
        Post updatedPost = findById(id);
        updatedPost.setBody(body);
        save(updatedPost);
        return updatedPost;
    }
}
