<template>
  <div id="main-content" class="container" align>
    <div class="row">
      <h4>Chat</h4>
    </div>
    <div class="row">
      <div class="col-md-12">
        <div class="form-group" align>
          <label for="connect">Chat connection:</label>
          <button
            id="connect"
            class="btn btn-default"
            type="submit"
            :disabled="connected == 1"
            @click.prevent="connect"
          >Connect</button>
          <button
            id="disconnect"
            class="btn btn-default"
            type="submit"
            :disabled="connected == 0"
            @click.prevent="disconnect"
          >Disconnect</button>
        </div>
        <form class="col-md-6" align>
          <div class="form-group">
            <label for="from">
              <br />Write your Message:
            </label>
            <input
              type="text"
              id="text"
              class="form-control"
              v-model="send_message"
              placeholder="Write a message..."
            />
            <div>
              <br />
              <button id="send" class="btn btn-default" type="submit" @click.prevent="send">Send</button>
            </div>
          </div>
        </form>
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
  name: "Chat",
  data() {
    return {
      received_messages: [],
      send_message: null,
      connected: 0,
      username: this.$parent.account.username
    };
  },
  methods: {
    send() {
      if (this.stompClient && this.stompClient.connected) {
        var from = this.username;
        var text = document.getElementById("text").value;
        this.stompClient.send(
          "/app/chat",
          JSON.stringify({ from: from, text: text }),
          {}
        );
      }
    },
    showMessageOutput(tick) {
      var item = document.getElementById("item");
      var td = document.createElement("td");
      td.style.wordWrap = "break-word";
      td.appendChild(
        document.createTextNode(
          tick.sender + ": " + tick.text + " (" + tick.time + ")"
        )
      );
      item.appendChild(td);
    },
    connect() {
      this.socket = new SockJS("http://localhost:4201/chat");
      this.stompClient = Stomp.over(this.socket);

      this.stompClient.connect(
        {},
        frame => {
          this.connected = 1;
          console.log(frame);
          this.stompClient.subscribe("/topic/chat", tick => {
            console.log(tick);
            this.received_messages.push(JSON.parse(tick.body));
            //showMessageOutput(JSON.parse(tick.body));
          });
        },
        error => {
          console.log(error);
          this.connected = 0;
        }
      );
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = 0;
    },

    tickleConnection() {
      this.connected ? this.disconnect() : this.connect();
    }
  },
  mounted() {}
};
</script>
<style scoped="">
</style>