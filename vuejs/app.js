// data is one function, because the variables are local without influence another instance of this component
Vue.component('greeting', {
  template: '<p>Hey there, I am a {{ name }}. <button v-on:click="changeName">Change Name</button></p>',
  data: function() {
    return {
      name: "Vitor"
    }
  },
  methods: {
    changeName: function() {
      this.name = "Join";
    }
  }
});


var one = new Vue({
  el: '#vue-app-one',
});

var two = new Vue({
  el: '#vue-app-two',
});
