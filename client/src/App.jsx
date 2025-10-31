import React, { useState, useEffect } from "react";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import PaymentForm from "./components/PaymentForm";
import TransactionTable from "./components/TransactionTable";
import { Container, Row, Col, Modal, Button } from "react-bootstrap";

const API_BASE = process.env.REACT_APP_API_BASE_URL;

function App() {
  const [transactions, setTransactions] = useState([]);
  const [lastResponse, setLastResponse] = useState(null);
  const [showModal, setShowModal] = useState(false);

  const fetchTransactions = async () => {
    try {
      const res = await axios.get(`${API_BASE}/payments/transactions/all`);
      setTransactions(res.data.reverse());
    } catch (err) {
      toast.error("Failed to load transactions");
      console.error(err);
    }
  };

  const handlePayment = async (formData) => {
    try {
      const res = await axios.post(`${API_BASE}/payments/process`, formData);
      setLastResponse(res.data);
      fetchTransactions();

      if (res.data.status === "AUTHORIZED") {
        toast.success("✅ Payment authorized successfully!");
      } else {
        toast.warn("⚠️ Payment declined!");
      }

      setShowModal(true);
    } catch (err) {
      toast.error("🚫 Payment request failed!");
      console.error(err);
    }
  };

  useEffect(() => {
    fetchTransactions();
  }, []);

  return (
    <div className="bg-light min-vh-100">
      <Container className="py-5">
        <Row className="justify-content-center mb-4">
          <Col md={10} className="text-center">
            <h2 className="fw-bold text-primary mb-3">
              💳 Secure Payment Gateway Simulation
            </h2>
            <p className="text-muted">
              Simulate Mastercard-style transactions between users and merchants
            </p>
          </Col>
        </Row>

        <Row className="justify-content-center mb-4">
          <Col md={10}>
            <div className="shadow-sm p-4 bg-white rounded-3">
              <PaymentForm onSubmit={handlePayment} />
            </div>
          </Col>
        </Row>

        <Row className="justify-content-center">
          <Col md={10}>
            <div className="shadow-sm bg-white p-3 rounded-3">
              <TransactionTable transactions={transactions} />
            </div>
          </Col>
        </Row>

        <ToastContainer position="top-right" autoClose={3000} />

        <Modal
          show={showModal}
          onHide={() => setShowModal(false)}
          centered
          backdrop="static"
        >
          <Modal.Header closeButton>
            <Modal.Title>Transaction Result</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            {lastResponse ? (
              <pre>{JSON.stringify(lastResponse, null, 2)}</pre>
            ) : (
              <p>No recent transaction.</p>
            )}
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={() => setShowModal(false)}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
      </Container>
    </div>
  );
}

export default App;
