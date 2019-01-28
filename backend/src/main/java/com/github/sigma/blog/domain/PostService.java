package com.github.sigma.blog.domain;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.util.Collections.unmodifiableList;

@ApplicationScoped
public class PostService implements Serializable {

    private static final Logger log = LogManager.getLogManager().getLogger(PostService.class.getName());

    @Inject
    PostRepository postRepository;

//    @PostConstruct
//    public void initTestData() {
//        final PostRequest request = PostRequest.of("ololo");
//        save(request);
//    }

    @Transactional
    public PostResponse save(final PostRequest sendPostRequest) {
        if (sendPostRequest == null) {
            log.warning("sendPostRequest is null. exiting without saving post...");
            return null;
        }

        final String postText = sendPostRequest.getPostText();
        if (isNotNullOrBlank(postText)) {
            log.warning("postText is null. exiting without saving post...");
            return null;
        }

        final Post savedPost = postRepository.save(new Post(postText));
        return PostResponse.of(savedPost);
    }

    public List<PostResponse> findPosts() {
        final List<PostResponse> result = new ArrayList<>();
        final List<Post> postsInDescendOrder = postRepository.getAll();
        for (Post post: postsInDescendOrder) {
            result.add(PostResponse.of(post));
        }
        return unmodifiableList(result);
    }

    public PostResponse findOnePost(UUID id) throws PostNotFoundException {
        Post post = postRepository.findById(id);
        if (post == null) throw new PostNotFoundException(id);
        final PostResponse result = PostResponse.of(post);
        return result;
    }

    public boolean isPostExists(UUID id) {
        return postRepository.count(id) > 0;
    }

    public PostResponse editOnePost(UUID id, String body) throws PostNotFoundException {
        Post post = postRepository.update(id, body);
        if (post == null) throw new PostNotFoundException(id);
        final PostResponse result = PostResponse.of(post);
        return result;
    }

    /* Helpers */

    private boolean isNotNullOrBlank(String postText) {
        if (postText == null) return true;
        if (postText.trim().equals("")) return true;
        return false;
    }

    public void delete(String uuid) {
        postRepository.delete(UUID.fromString(uuid));
    }
}
