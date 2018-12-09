// Events: https://www.w3schools.com/tags/ref_eventattributes.asp
// Events Modifiers: https://vuejs.org/v2/guide/events.html#Event-Modifiers
var one = new Vue({
  el: '#vue-app-one',
  data: {
    title: 'Vue App One'
  },
  methods: {

  },
  computed: {
    greet: function() {
      return 'Hello from App One';
    }
  }
});

var two = new Vue({
  el: '#vue-app-two',
  data: {
    title: 'Vue App Two'
  },
  methods: {
    changeTitle: function() {
      one.title = "Title changed";
    }
  },
  computed: {
    greet: function() {
      return 'Hello from App Two';
    }
  }
});

two.title = "Changed from outside";
