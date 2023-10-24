const canvas = document.getElementById("drawCanvas");
const ctx = canvas.getContext("2d");
const socketRoute = document.getElementById("ws-route").value;
const socket = new WebSocket(socketRoute.replace("http", "ws"));



//socket.onopen = () => socket.send("Test message");
  

// Load the user image
const userImage = new Image();
userImage.src = "/assets/images/awesome-face-png-1.png"; // Update the image path
userImage.height = 50; // Desired width of the user image
userImage.width = 50; // Desired height of the user image

let userX = Math.round(canvas.width / 2* Math.random()); // Initial user position X
let userY = Math.round(canvas.height / 2 * Math.random()); // Initial user position Y
const userSpeed = 10; // Adjust the user movement speed

// Event listener for arrow key presses
document.addEventListener("keydown", (e) => {
  switch (e.key) {
    case "ArrowLeft":
      userX -= userSpeed;
      break;
    case "ArrowRight":
      userX += userSpeed;
      break;
    case "ArrowUp":
      userY -= userSpeed;
      break;
    case "ArrowDown":
      userY += userSpeed;
      break;
  }
  drawUser();
});

// Function to clear the canvas
function clearCanvas() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);
}

// Function to draw the user image
function drawUser() {
  clearCanvas();
  ctx.drawImage(userImage, userX, userY);
}

// Load the user image and draw it initially
userImage.onload = () => {
  drawUser();
};

socket.onopen = () => socket.send("New user connected");

onkeydown = (event) =>{
  socket.send((userX + ":" + userY));
}

socket.onmessage = (event) => {
  const xyCoordinates = event.data.split(':');
  if (xyCoordinates.length === 2) {
    userX = parseInt(xyCoordinates[0]);
    userY = parseInt(xyCoordinates[1]);
    drawUser();
  }
};