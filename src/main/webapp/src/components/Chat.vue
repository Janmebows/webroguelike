<template>
  <div id="main-content" class=" jumbotron">
    <h4>Chat</h4>
    <div class="row">
      <div class="col-sm-12 col-md-12 col-lg-6">
        <div class="form-group d-none">
          <div class="custom-control custom-switch">
            <input
              type="checkbox"
              class="custom-control-input"
              id="customSwitch1"
              checked=""
            />
            <label class="custom-control-label" for="customSwitch1"
              >Toggle this switch element</label
            >
          </div>
        </div>
        <div class="form-group d-none">
          <label for="connect">Chat connection:</label>
          <button
            id="connect"
            class="btn btn-default"
            type="submit"
            :disabled="connected == 1"
            @click.prevent="connect"
          >
            Connect
          </button>
          <button
            id="disconnect"
            class="btn btn-default"
            type="submit"
            :disabled="connected == 0"
            @click.prevent="disconnect"
          >
            Disconnect
          </button>
        </div>
        <form class="row">
          <div class="col-md-8">
            <label for="from"> <br />Write your Message: </label>
            <input
              type="text"
              id="text"
              class="form-control"
              v-model="send_message"
              placeholder="Write a message..."
            />
          </div>
          <div class="col-md-4 d-flex align-items-end">
            <button
              id="send"
              class="btn btn-primary"
              type="submit"
              @click.prevent="send"
            >
              Send
            </button>
          </div>
        </form>

        <table id="conversation" class="table">
          <thead>
            <tr>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in received_messages" :key="item">
              <td>
                <div
                  class="toast show"
                  role="alert"
                  aria-live="assertive"
                  aria-atomic="true"
                >
                  <div class="toast-header">
                    <strong class="mr-auto">{{ item.sender }} said</strong>
                    <small>{{ item.time }}</small>
                  </div>
                  <div class="toast-body">
                    {{ item.text }}
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
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
  mounted() {
    this.connect();
    if (!this.$parent.authenticated) {
      this.$router.push("/login");
    }
  }
};
</script>
<style scoped="">
.table th, .table td {
    border-top: 0px solid #444;
}
</style>