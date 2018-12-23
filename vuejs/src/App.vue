<template>
  <div>
    <app-header></app-header>
    <router-view></router-view>
  </div>
</template>

<script>
import AddBlog from './components/AddBlog.vue'
import ShowBlogs from './components/ShowBlogs.vue'
import ListBlogs from './components/ListBlogs.vue'
import header from './components/header.vue'

export default {
  components: {
    'add-blog': AddBlog,
    'show-blogs': ShowBlogs,
    'list-blogs': ListBlogs,
    'app-header': header
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
