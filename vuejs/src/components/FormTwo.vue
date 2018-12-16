<template>
  <form-helper>
    <div slot="form-header">
      <h3>This is the title of the form</h3>
      <p>Information about the form</p>
    </div>
    <div slot="form-fields">
      <input type="text" placeholder="name" required/>
      <input type="password" placeholder="password" required/>
    </div>
    <div slot="form-controls">
      <button type="button" v-on:click="handleSubmit">Login</button>
    </div>
  </form-helper>
</template>

<script>

import FormHelper from './FormHelper.vue'

export default {
  components: {
    'form-helper': FormHelper
  },
  data () {
    return {

    }
  },
  methods: {
    handleSubmit: function() {
      alert('Thanks for logging in (form two)');
    }
  }
}
</script>

<style lang="css">

body{
    margin: 0;
    font-family: 'Nunito SemiBold';
}

</style>
