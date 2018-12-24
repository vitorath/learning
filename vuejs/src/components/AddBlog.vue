<template lang="html">
  <div id="add-blog">
    <h2>Add a New Blog Post</h2>
    <form v-if="!submitted">
      <label>Blog Title:</label>
      <input type="text" v-model.lazy="blog.title" required/>
      <label>Blog Content</label>
      <textarea v-model.lazy="blog.content"></textarea>
      <div id="checkboxes">
        <label>Ninjas</label>
        <input type="checkbox" v-model="blog.categories" value="ninjas">
        <label>Wizard</label>
        <input type="checkbox" v-model="blog.categories" value="wizards">
        <label>Mario</label>
        <input type="checkbox" v-model="blog.categories" value="mario">
        <label>Cheese</label>
        <input type="checkbox" v-model="blog.categories" value="cheese">
      </div>
      <label>Author:</label>
      <select v-model="blog.author">
        <option v-for="author in authors">{{ author }}</option>
      </select>
      <button v-on:click.prevent="post">Add Blog</button>
    </form>
    <div v-if="submitted">
      <h3>Thanks for adding your post</h3>
    </div>
    <div id="preview">
      <h3>Preview Blog</h3>
      <p>Blog title: {{ blog.title }}</p>
      <p>Blog content:</p>
      <p>{{ blog.content }}</p>
      <p>Blog Categories:</p>
      <ul>
        <li v-for="category in blog.categories">{{ category }}</li>
      </ul>
      <p>Author: {{ blog.author }}</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      blog: {
        title: "",
        content: "",
        categories: [],
        author: ""
      },
      authors: ['The Net Ninja', 'The Angular Avenger', 'The Vue Vindicator'],
      submitted: false
    }
  },
  methods: {
    post: function() {
      console.log('click')
      this.$http.post('https://nn-vue-playlist-9a346.firebaseio.com/posts.json',this.blog).then(function(data) {
        console.log(data);
        this.submitted = true
      });
    }
  }
}
</script>

<style lang="css">
#add-blog *{
  box-sizing: border-box;
}

#add-blog {
  margin: 20px auto;
  max-width: 500px;
}

label {
  display: block;
  margin: 20px 0 10px;
}

input[type="text"], textarea {
  display: block;
  width: 100%;
  padding: 8px;
}

#preview {
  padding: 10px 20px;
  border: 1px dotted #ccc;
  margin: 30px 0;
}

h3 {
  margin-top: 10px;
}

#checkboxes input {
  display: inline-block;
  margin-right: 10px;
}

#checkboxes label {
  display: inline-block;
}

</style>
