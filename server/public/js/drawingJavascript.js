const canvas = document.getElementById("drawCanvas");
const ctx = canvas.getContext("2d");
const socketRoute = document.getElementById("ws-route").value;
const socket = new WebSocket(socketRoute.replace("http", "ws"));
console.log("starting");

//socket.onopen = () => socket.send("Test message");
  
// Load the user image
const userImage = new Image();
userImage.src = "/assets/images/awesome-face-png-1.png"; // Update the image path
userImage.height = 50; // Desired width of the user image
userImage.width = 50; // Desired height of the user image

//let userX = Math.round(canvas.width / 2* Math.random()); // Initial user position X
//let userY = Math.round(canvas.height / 2 * Math.random()); // Initial user position Y
let userX = 0.0; // Initial user position X
let userY = 0.0; // Initial user position Y
const userSpeed = 10; // Adjust the user movement speed

// Event listener for arrow key presses
document.addEventListener("keydown", (e) => {
  switch (e.key) {
    case "ArrowLeft":
      if (userX > 0){
        userX -= userSpeed;
      }
      break;
    case "ArrowRight":
      if (userX < 900){
        userX += userSpeed;
      }
      break;
    case "ArrowUp":
      if (userY > 0){
        userY -= userSpeed;
      }
      break;
    case "ArrowDown":
      if (userY < 300){
        userY += userSpeed;
      }
      break;
  }
  drawUser(userX, userY);
});

// Function to clear the canvas
function clearCanvas() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);
}

// Function to draw the user image
function drawUser(x,y) {
  //clearCanvas();
  ctx.drawImage(userImage, x, y);
}

// Load the user image and draw it initially
userImage.onload = () => {
  drawUser(userX, UserY);
};

socket.onopen = () => socket.send("New user connected");

onkeydown = (event) =>{
  socket.send((userX + ":" + userY));
}

socket.onmessage = (event) => {
  console.log("got message"); 
  console.log(event.data);
  clearCanvas();
  const allCoordinate = event.data.split(' ')
    for (const i of allCoordinate) {
      const xyCoordinates = i.split(':'); // Modify the split delimiter
      if (xyCoordinates.length === 2) {
        const x = parseFloat(xyCoordinates[0]); // Use parseFloat for double values
        const y = parseFloat(xyCoordinates[1]);
        // userX = x; // Set the userX and userY with the parsed values
        // userY = y;
        drawUser(x,y);
      }
    
    }
};
