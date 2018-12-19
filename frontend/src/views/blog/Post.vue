<template>
  <div class="post">
    <h1>{{ post.title }}!</h1>
    <p>{{ post.body }}</p>
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
