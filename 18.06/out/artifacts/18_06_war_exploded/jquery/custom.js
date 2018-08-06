$(document).ready(function() {
   
      alert("Hello, world!");
      var x = [1, 2, 3, 4, 5];

      for (var i = 0; i < x.length; i++) {
    	   console.log(i);
      }
      
      scope() // window, 0
  	scope.call("foobar", [1,2]);  //==> "foobar", 1
  	scope.apply("foobar", [1,2]); //==> "foobar", 2

});

function scope() {
	   console.log(this, arguments.length);
	}

	