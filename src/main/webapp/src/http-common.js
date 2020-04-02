import axios from "axios";

export default axios.create({
  baseURL: "http://localhost:4201/api",
  headers: {
    "Content-type": "application/json",
  }
});