import React, { useEffect, useState } from "react";

function App() {
  const [events, setEvents] = useState([]);
  const [form, setForm] = useState({
    name: "",
    email: "",
    phone: "",
    eventId: ""
  });
  const [msg, setMsg] = useState("");
  const [editId, setEditId] = useState(null);

  // Load events
  useEffect(() => {
    fetch("http://localhost:8080/api/events")
      .then(res => res.json())
      .then(data => setEvents(data));
  }, []);

  // Delete Event
  const deleteEvent = (id) => {
    fetch(`http://localhost:8080/api/events/${id}`, {
      method: "DELETE"
    }).then(() => {
      setEvents(events.filter(event => event.id !== id));
    });
  };

  // Edit Event
  const editEvent = (event) => {
    setForm({
      name: event.name,
      email: "",
      phone: "",
      eventId: event.id
    });
    setEditId(event.id);
  };

  // Update Event
  const updateEvent = (e) => {
    e.preventDefault();

    fetch(`http://localhost:8080/api/events/${editId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        name: form.name,
        date: "2026-03-10",
        location: "Updated Location"
      })
    })
      .then(res => res.json())
      .then(data => {

        setEvents(events.map(ev =>
          ev.id === editId ? data : ev
        ));

        setEditId(null);
        setForm({ name: "", email: "", phone: "", eventId: "" });

      });
  };

  // Form change
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // Submit registration
  const submitForm = (e) => {
    e.preventDefault();

    fetch("http://localhost:8080/api/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form)
    })
      .then(res => res.json())
      .then(() => {
        setMsg("✅ Registration Successful");
        setForm({ name: "", email: "", phone: "", eventId: "" });
      });
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>📅 Events List</h2>

      <ul>
        {events.map(e => (
          <li key={e.id}>
            {e.name} – {e.date} – {e.location}

            <button
              style={{ marginLeft: "10px" }}
              onClick={() => editEvent(e)}
            >
              Edit
            </button>

            <button
              style={{ marginLeft: "10px" }}
              onClick={() => deleteEvent(e.id)}
            >
              Delete
            </button>

          </li>
        ))}
      </ul>

      <hr />

      <h2>📝 Register for Event</h2>

      <form onSubmit={editId ? updateEvent : submitForm}>

        <input
          name="name"
          placeholder="Name"
          value={form.name}
          onChange={handleChange}
          required
        />
        <br /><br />

        <input
          name="email"
          placeholder="Email"
          value={form.email}
          onChange={handleChange}
          required
        />
        <br /><br />

        <input
          name="phone"
          placeholder="Phone"
          value={form.phone}
          onChange={handleChange}
          required
        />
        <br /><br />

        <select
          name="eventId"
          value={form.eventId}
          onChange={handleChange}
          required
        >
          <option value="">Select Event</option>

          {events.map(e => (
            <option key={e.id} value={e.id}>
              {e.name}
            </option>
          ))}

        </select>

        <br /><br />

        <button type="submit">
          {editId ? "Update Event" : "Register"}
        </button>

      </form>

      <h3>{msg}</h3>

    </div>
  );
}

export default App;