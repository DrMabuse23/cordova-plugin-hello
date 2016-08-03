/*global cordova, module*/
module.exports = {
    lastLocation: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "LocationPlugin", "lastLocation", [name]);
    },
    getLocation: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "LocationPlugin", "getLocation", [name]);
    }
};
