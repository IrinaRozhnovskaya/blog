<template>
  <div class="post">
    <div v-html="render(post.body)"/>
  </div>
</template>

<script>
  import MarkdownIt from 'markdown-it';
  const md = new MarkdownIt({ html: true });
  export default {
    name: 'Post',
    props: {
      post: {
        title: 'Title is undefined',
        body: 'Body is undefined',
      }
    },
    methods: {
      render(markdown) {
        return md.render(markdown);
      },
    },
  };
</script>

<style>
  ul {
    list-style: none;
  }

</style>
