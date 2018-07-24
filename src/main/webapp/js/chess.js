var websocket = null;
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://localhost:8080/chess/websocket");
} else {
    console.log('browser not support websocket')
}

websocket.onerror = function () {
};

websocket.onopen = function () {
};

websocket.onmessage = function (event) {
    console.log(event.data);
    var data = event.data;
    app.chess.log += data + '\n';

    var str = data.split("@");
    switch (str[0]) {
        case "ready":
            break;
        case "start":
            break;
        case "flip":
            break;
        case "move":
            break;
        case "color":
            app.chess.color = str[1];
            break;
        case "over":
            break;
        default:
            break;
    }
}

websocket.onclose = function () {
};

window.onbeforeunload = function () {
    webSocketClose();
};

function webSocketClose() {
    websocket.close();
}

function webSocketsendMessage(message) {
    websocket.send(message);
}

var app = new Vue({
	el: '#app',
	data: {
		chess: {
			log: ''
		},
	},
	methods: {
		chessReady: function() {
            var msg = "ready";
            websocket.send(msg);
		},
        pieceMove: function() {
            console.log(app.chess.from);
            console.log(app.chess.to);
            var msg = "move@" + app.chess.from + ":" + app.chess.to;
			websocket.send(msg);
		},
        pieceEat: function() {
            console.log(app.chess.from);
            console.log(app.chess.to);
            var msg = "eat@" + app.chess.from + ":" + app.chess.to;
            websocket.send(msg);
        },
        pieceExchange: function() {
            console.log(app.chess.from);
            console.log(app.chess.to);
            var msg = "exchange@" + app.chess.from + ":" + app.chess.to;
            websocket.send(msg);
        },
        pieceFlip: function () {
			var msg = "flip@" + app.chess.from;
			websocket.send(msg);
        },
        chessLose: function() {
			var msg = "lose@";
			websocket.send(msg);
		}
	}
});
