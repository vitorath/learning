<template>
  <div>
    <form-helper>
      <div slot="form-header">
        <h3>Form One - Contact Us</h3>
        <p>Fill in this form to contact us</p>
      </div>
      <div slot="form-fields">
        <input type="text" placeholder="name" required>
        <label>Your Message:</label>
        <textarea rows="8" cols="80"></textarea>
      </div>
      <div slot="form-controls">
        <button type="button" v-on:click="handleSubmit">Send</button>
      </div>
    </form-helper>
  </div>
</template>

<script type="text/javascript">

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
      alert('Thanks for sunmittin form one & contacting us')
    }
  }
}

</script>

<style>

body {
  margin: 0;
  font-family: 'Nunito SemiBold';
}

</style>
