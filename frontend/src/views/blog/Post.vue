<template>
  <div class="post">
    <h2>{{ post.title }}!</h2>
    <div>{{ post.body }}</div>
  </div>
</template>

<script>
  export default {
    name: 'Post',
    props: {
      post: {
        title: 'Title is undefined',
        body: 'Body is undefined',
      }
    },
  };
</script>

<style>
  ul {
    list-style: none;
  }
</style>
