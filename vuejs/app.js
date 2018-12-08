// Events: https://www.w3schools.com/tags/ref_eventattributes.asp
// Events Modifiers: https://vuejs.org/v2/guide/events.html#Event-Modifiers
new Vue({
  el: '#vue-app',
  data: {

  },
  methods: {
    logName: function() {
      console.log("Log keyup Name");
    },
    logAge: function() {
      console.log("Log keyup enter Age");
    },
    logJob: function() {
        console.log("Log keyup alt + enter Job");
    }
  }
});
