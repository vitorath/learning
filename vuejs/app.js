new Vue({
  el: '#vue-app',
  data: {
    name: 'Vitor',
    job: 'AI Programmer',
    website: 'https://www.google.com',
    websiteTag: '<a href="https://www.google.com">Google</a>'
  },
  methods: {
    greet: function(time) {
      return 'Good ' + time + ' ' + this.name;
    }
  }
});
