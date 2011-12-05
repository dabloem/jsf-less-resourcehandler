#Usage#
Add the lib to your web-app, and you can use h:outputStylesheet name="somefile.less"</code>.
There is also a less component, which add the less.js resource to the page for client-side compilation of the less resource:

    <ldn:lessOutputStylesheet name="simple.css"/>

note: suffix the less/css resource .css instead of .less, otherwise the resourcehandler will compile server-side.

###Example simple.less###

    .box-shadow (@x: 0, @y: 0, @blur: 1px, @alpha) {
      @val: @x @y @blur rgba(0, 0, 0, @alpha);

    box-shadow:         @val;
     -webkit-box-shadow: @val;
     -moz-box-shadow:    @val;
    }

    .box { @base: #{backingBean.color};
      color:        saturate(@base, 0%);
      border-color: lighten(@base, 30%);
      div { .box-shadow(0, 0, 5px, 0.4) }
    }

Note: @import doesn't work server-side nor client-side (yet).

more information [here](dabloem.blogspot.com "JSF Blog")