try {
    require("source-map-support").install();
} catch(err) {
}
require("./out/goog/bootstrap/nodejs.js");
require("./out/cljs_firmata.js");
goog.require("cljs_firmata.core");
goog.require("cljs.nodejscli");
