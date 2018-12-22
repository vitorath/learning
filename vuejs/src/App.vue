<template>
  <div>
    <show-blogs></show-blogs>
    <list-blogs></list-blogs>
  </div>
</template>

<script>
import AddBlog from './components/AddBlog.vue'
import ShowBlogs from './components/ShowBlogs.vue'
import ListBlogs from './components/ListBlogs.vue'

export default {
  components: {
    'add-blog': AddBlog,
    'show-blogs': ShowBlogs,
    'list-blogs': ListBlogs
  },
  data () {
    return {
    }
  },
  methods: {

  }
}
</script>

<style>

body {
  margin: 0;
  font-style: 'Nunito SemiBold';
}

</style>
