package com.github.sigma.blog.domain;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static com.github.sigma.blog.domain.Post.*;
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

    public Long count(UUID id) {
        return em.createNamedQuery(COUNT, Long.class)
                 .setParameter("id", id)
                 .getSingleResult();
    }

    public Post update(UUID id, String title, String body) {
        Post updatedPost = findById(id);
        if (null == updatedPost) return null;
        updatedPost.setTitle(title);
        updatedPost.setBody(body);
        save(updatedPost);
        return updatedPost;
    }

    public void delete(UUID id) {
        Post post = findById(id);
        em.remove(post);
    }
}
