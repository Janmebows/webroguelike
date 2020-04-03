

<template>
  <div>
    <div id="main-content" class="container">
      <div class="row">
        <div class="col-md-6">
          <form class="form-inline">
            <div class="form-group">
              <label for="connect">WebSocket connection:</label>
              <button
                id="connect"
                class="btn btn-default"
                type="submit"
                :disabled="connected == true"
                @click.prevent="connect"
              >Connect</button>
              <button
                id="disconnect"
                class="btn btn-default"
                type="submit"
                :disabled="connected == false"
                @click.prevent="disconnect"
              >Disconnect</button>
            </div>
          </form>
        </div>
        <div class="col-md-6">
          <form class="form-inline">
            <div class="form-group">
              <label for="from">What is your name?</label>
              <input type="from" id="from" class="form-control" placeholder="Your name here..." />
              <label for="msg">What is your msg?</label>
              <input
                type="text"
                id="text"
                class="form-control"
                v-model="send_message"
                placeholder="Write a message..."
              />
              <div>
                <button id="send" class="btn btn-default" type="submit" @click.prevent="send">Send</button>
              </div>
            </div>
          </form>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <table id="conversation" class="table table-striped">
            <thead>
              <tr>
                <th>Messages</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in received_messages" :key="item">
                <td>{{ item }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
export default {
  name: "websocketdemo",
  data() {
    return {
      received_messages: [],
      send_message: null,
      connected: false
    };
  },
  methods: {
    send() {
      console.log("Send message:" + this.send_message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = { name: this.send_message };
        console.log(JSON.stringify(msg));
        var from = document.getElementById("from").value;
        var text = document.getElementById("text").value;
        this.stompClient.send(
          "/app/chat",
          JSON.stringify({ from: from, text: text }),
          {}
        );
      }
    },
    connect() {
      this.socket = new SockJS("http://localhost:4201/chat");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        {},
        frame => {
          this.connected = true;
          console.log(frame);
          this.stompClient.subscribe("/topic/chat", tick => {
            console.log(tick);
            this.received_messages.push(JSON.parse(tick.body));
          });
        },
        error => {
          console.log(error);
          this.connected = false;
        }
      );
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    tickleConnection() {
      this.connected ? this.disconnect() : this.connect();
    }
  },
  mounted() {
    // this.connect();
  }
};
</script>
<style scoped="">
</style>