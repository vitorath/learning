<template>
  <div>
    <add-blog></add-blog>
  </div>
</template>

<script>
import AddBlog from './components/AddBlog.vue'

export default {
  components: {
    'add-blog': AddBlog
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
