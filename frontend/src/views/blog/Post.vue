<template>
  <div class="post">
    <div v-html="render(post.body)"/>
    <br/>
  </div>
</template>

<script>
  import MarkdownIt from 'markdown-it';
  // const md = new MarkdownIt({ html: true });
  const md = new MarkdownIt({
    html: true,
    breaks: true,
    linkify: true,
    typographer: true,
  });
  // const md = new MarkdownIt('zero');
  // const md = new MarkdownIt('default');
  // const md = new MarkdownIt('commonmark');
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
