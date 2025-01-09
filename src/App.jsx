import "./App.css";
import { User, MessageCircle, X, Heart } from "lucide-react";
import React, { useState, useEffect, useRef } from "react";

const fetchRandomProfile = async () => {
  const response = await fetch("http://localhost:8084/profiles/random");
  if (!response.ok) {
    throw new Error("Failed to fetch profile");
  }
  return response.json();
};

const saveSwipe = async (profileId) => {
  const response = await fetch("http://localhost:8084/matches", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ profileId }),
  });
  if (!response.ok) {
    throw new Error("Failed to save swipe");
  }
};

const fetchMatches = async () => {
  const response = await fetch("http://localhost:8084/matches");
  if (!response.ok) {
    throw new Error("Failed to fetch profile");
  }
  return response.json();
};

const fetchConversation = async (conversationId) => {
  const response = await fetch(
    `http://localhost:8084/conversations/${conversationId}`
  );
  if (!response.ok) {
    throw new Error("Failed to fetch conversation");
  }
  return response.json();
};

const sendMessage = async (conversationId, message) => {
  const response = await fetch(
    `http://localhost:8084/conversations/${conversationId}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ messageText: message, authorId: "user" }),
    }
  );
  if (!response.ok) {
    throw new Error("Failed to send message");
  }
};

const ProfileSelector = ({ profile, onSwipe }) =>
  profile ? (
    <div className="rounded-lg overflow-hidden bg-white shadow-lg">
      <div className="relative">
        <img src={"http://127.0.0.1:8081/" + profile.imageUrl} />
        <div className="absolute bottom-0 left-0 right-0 text-white p-4 bg-gradient-to-t from-black">
          <h2 className="text-3xl font-bold">
            {profile.firstName} {profile.lastName}, {profile.age}
          </h2>
        </div>
      </div>
      <div className="p-4">
        <p className="text-gray-600">{profile.bio}</p>
      </div>
      <div className="p-4 flex justify-center space-x-4 text-white">
        <button
          className="bg-red-500 rounded-full p-4 hover:bg-red-700"
          onClick={() => onSwipe(profile.id, "left")}
        >
          <X size={24} />
        </button>
        <button
          className="bg-green-500 rounded-full p-4 hover:bg-green-700"
          onClick={() => onSwipe(profile.id, "right")}
        >
          <Heart size={24} />
        </button>
      </div>
    </div>
  ) : (
    <div>Loading...</div>
  );

const MatchesList = ({ matches, onSelectMatch }) => {
  return (
    <div className="rounded-lg shadow-lg p-4">
      <h2 className="text-2xl font-bold mb-4">Matches</h2>
      <ul>
        {matches.map((match, index) => (
          <li key={index} className="mb-2">
            <button
              className="w-full hover:bg-gray-100 rounded flex items-center"
              onClick={() => onSelectMatch(match.profile, match.conversationId)}
            >
              <img
                src={"http://127.0.0.1:8081/" + match.profile.imageUrl}
                className="w-16 h-16 rounded-full mr-3 object-cover"
                alt=""
              />
              <div>
                <span>
                  <h3 className="font-bold">
                    {match.profile.firstName} {match.profile.lastName}
                  </h3>
                </span>
              </div>
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

const ChatScreen = ({ currentMatch, conversation, refreshState }) => {
  const [input, setInput] = useState("");
  const chatContainerRef = useRef(null);
  const handleSend = async (conversation, input) => {
    if (input.trim()) {
      await sendMessage(conversation, input);
      setInput("");
    }
    refreshState();
  };

  useEffect(() => {
    if (chatContainerRef.current) {
      chatContainerRef.current.scrollTop =
        chatContainerRef.current.scrollHeight;
    }
  }, [conversation.messages]);

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      e.preventDefault(); // Prevent new line in the input field
      handleSend(conversation.id, input); // Call handleSend function
    }
  };

  return currentMatch ? (
    <div className="rounded-lg shadow-lg p-4">
      <h2 className="text-2xl font-bold mb-4">
        Chat with {currentMatch.firstName} {currentMatch.lastName}
      </h2>
      <div
        className="h-[50vh] border rounded overflow-y-auto mb-4 p-2"
        ref={chatContainerRef}
      >
        {conversation.messages.map((message, index) => (
          <div
            key={index}
            className={`flex ${
              message.authorId === "user" ? "justify-end" : "justify-start"
            } mb-4`}
          >
            <div
              className={`flex items-end ${
                message.authorId === "user" ? "flex-row-reverse" : "flex-row"
              }`}
            ></div>
            {message.authorId === "user" ? (
              <User size={15} />
            ) : (
              <img
                src={`http://127.0.0.1:8081/${currentMatch.imageUrl}`}
                className="w-11 h-11 rounded-full"
              />
            )}
            <div
              className={`max-w-xs px-4 py-2 rounded-2xl ${
                message.authorId === "user"
                  ? "bg-blue-500 text-white ml-2"
                  : "bg-gray-200 text-gray-800 mx-2"
              }`}
            >
              {message.messageText}
            </div>
          </div>
        ))}
      </div>
      <div className="flex">
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={handleKeyDown}
          className="border flex-1 rounded p-2 mx-2"
          placeholder="Type your message..."
        />
        <button
          className="bg-blue-500 text-white rounded p-2"
          onClick={() => handleSend(conversation.id, input)}
        >
          Send
        </button>
      </div>
    </div>
  ) : (
    <div>Loading...</div>
  );
};

function App() {
  const loadRandomProfile = async () => {
    try {
      const profile = await fetchRandomProfile();
      setCurrentProfile(profile);
    } catch (error) {
      console.error(error);
    }
  };

  const loadMatches = async () => {
    try {
      const matches = await fetchMatches();
      setMatches(matches);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    loadRandomProfile();
    loadMatches();
  }, {});

  const [currentScreen, setCurrentScreen] = useState("profile");
  const [currentProfile, setCurrentProfile] = useState(null);
  const [matches, setMatches] = useState([]);
  const [currentMatchAndConversation, setCurrentMatchAndConversation] =
    useState({
      match: {},
      conversation: [],
    });
  const onSwipe = async (profileId, direction) => {
    if (direction === "right") {
      await saveSwipe(profileId);
      await loadMatches();
    }
    loadRandomProfile();
  };

  const onSelectMatch = async (profile, conversationId) => {
    console.log(conversationId);
    const conversation = await fetchConversation(conversationId);
    setCurrentMatchAndConversation({
      match: profile,
      conversation: conversation,
    });
    setCurrentScreen("chat");
  };

  const refreshChatState = async () => {
    const conversation = await fetchConversation(
      currentMatchAndConversation.conversation.id
    );
    setCurrentMatchAndConversation({
      match: currentMatchAndConversation.match,
      conversation: conversation,
    });
  };

  const render = () => {
    switch (currentScreen) {
      case "profile":
        return <ProfileSelector profile={currentProfile} onSwipe={onSwipe} />;
      case "matchers":
        return <MatchesList onSelectMatch={onSelectMatch} matches={matches} />;
      case "chat":
        return (
          <ChatScreen
            currentMatch={currentMatchAndConversation.match}
            conversation={currentMatchAndConversation.conversation}
            refreshState={refreshChatState}
          />
        );
    }
  };

  return (
    <>
      <div className="max-w-md mx-auto">
        <nav className="flex justify-between">
          <User
            className="w-6 h-6"
            onClick={() => setCurrentScreen("profile")}
          />
          <MessageCircle
            className="w-6 h-6"
            onClick={() => setCurrentScreen("matchers")}
          />
        </nav>
        {render()}
      </div>
    </>
  );
}

export default App;
