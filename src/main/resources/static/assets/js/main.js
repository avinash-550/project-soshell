// keep terminal always on
document.onmousedown = (e) => {
  e.preventDefault();
}


//  When user presses key enter 
document.getElementById('commandInput').onkeypress = async function (e) {
  if (!e) e = window.event;
  var keyCode = e.code || e.key;
  if (keyCode == 'Enter') {
    let value = document.getElementById('commandInput').value;
    console.log('Input Value:', value);

    // Update inputText field
    document.getElementById('inputText').textContent = value;

    // loading text
    document.getElementById('commandInput').value = 'Waiting for a response from the server...';

    // Update server response
    updateOutputText(value);

    // Clear the input field
    document.getElementById('commandInput').value = '';
    return false;
  }
}


// udpate output
async function updateOutputText(command) {
  const commandBreakUp = command.split(" ");
  if(commandBreakUp[0] == "user" && commandBreakUp[1] == "login"){
    console.log("login request");
    const response = await fetchData(command);
    if(response == "login successful"){
      console.log("login successful");
      createSession(commandBreakUp[2]);
    }
    appendOutputText(response);
    return;
  }

  if(commandBreakUp[0] == "user" && commandBreakUp[1] == "logout"){
    console.log("logout request");
    logout();
    appendOutputText("user logged out successfully");
    return; 
  }

  const userLoggedIn = checkLogin();
  if(!userLoggedIn && commandBreakUp[1] != "signup"){
    appendOutputText("no active session found please login");
    return;
  }
  
  // server call in background and update 
  const output = await fetchData(command);
  appendOutputText(output);
}

function appendOutputText(value) {
  console.log("appending");
  const initialVal = document.getElementById('outputText').innerHTML;
  document.getElementById('outputText').innerHTML = '$ ' + value + '<br><br>' + initialVal;
}

// update actions
function updateActionText(value) {
  document.getElementById('actionText').textContent = value;
}


// asynchronous function to make the API call
async function fetchData(value) {
  try {
    // API endpoint
    const apiUrl = '/process';

    // request body
    const requestBody = {
      command: value
    };

    // Make the POST request using the fetch function
    const response = await fetch(apiUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        // Add any additional headers if needed
      },
      body: JSON.stringify(requestBody), // Convert data to JSON format
    });

    // Check if the request was successful (status code 2xx)
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    // Parse the response data as JSON
    const data = response.text();
    console.log(data);
    return data;
  } catch (error) {
    // Handle any errors that occurred during the fetch
    console.error('Error fetching data:', error.message);
  }
}


// check login
function checkLogin() {
  const sessionData = getCookie('session');

  if (sessionData) {
      console.log('Logged in as:', sessionData);
      return true;
  } else {
      console.log('Not logged in');
      return false;
  }
}


// create session
function createSession(userData) {
  // Set a session cookie with the user data
  setCookie('session', userData, 1);
}

// Function to set a cookie
function setCookie(name, value, days) {
  var expires = "";
  if (days) {
      var date = new Date();
      date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
      expires = "; expires=" + date.toUTCString();
  }
  document.cookie = name + "=" + value + expires + "; path=/";
}

// Function to get the value of a cookie by name
function getCookie(name) {
  var nameEQ = name + "=";
  var cookies = document.cookie.split(';');
  for (var i = 0; i < cookies.length; i++) {
      var cookie = cookies[i];
      while (cookie.charAt(0) === ' ') {
          cookie = cookie.substring(1, cookie.length);
      }
      if (cookie.indexOf(nameEQ) === 0) {
          return cookie.substring(nameEQ.length, cookie.length);
      }
  }
  return null;
}

function logout(){
  eraseCookie("session");
}

// Function to delete a cookie by name
function eraseCookie(name) {
  document.cookie = name + "=; Max-Age=-99999999;";
}