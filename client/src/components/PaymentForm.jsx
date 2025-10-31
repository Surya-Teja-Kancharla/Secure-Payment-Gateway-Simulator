import React, { useState } from "react";
import { Form, Row, Col, Button } from "react-bootstrap";
import { BsCreditCard2Front } from "react-icons/bs";

const PaymentForm = ({ onSubmit }) => {
  const [form, setForm] = useState({
    userId: 1,
    merchantId: 1,
    amount: 1500,
    cardToken: "TOK-1111-2222",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(form);
  };

  return (
    <Form onSubmit={handleSubmit}>
      <div className="d-flex align-items-center mb-3">
        <BsCreditCard2Front size={24} className="me-2 text-primary" />
        <h5 className="mb-0 text-primary">Payment Details</h5>
      </div>

      <Row className="mb-3">
        <Col md={3}>
          <Form.Group>
            <Form.Label>User ID</Form.Label>
            <Form.Control
              type="number"
              name="userId"
              value={form.userId}
              onChange={handleChange}
            />
          </Form.Group>
        </Col>
        <Col md={3}>
          <Form.Group>
            <Form.Label>Merchant ID</Form.Label>
            <Form.Control
              type="number"
              name="merchantId"
              value={form.merchantId}
              onChange={handleChange}
            />
          </Form.Group>
        </Col>
        <Col md={3}>
          <Form.Group>
            <Form.Label>Amount</Form.Label>
            <Form.Control
              type="number"
              name="amount"
              value={form.amount}
              onChange={handleChange}
            />
          </Form.Group>
        </Col>
        <Col md={3}>
          <Form.Group>
            <Form.Label>Card Token</Form.Label>
            <Form.Control
              type="text"
              name="cardToken"
              value={form.cardToken}
              onChange={handleChange}
            />
          </Form.Group>
        </Col>
      </Row>

      <div className="text-center mt-3">
        <Button variant="primary" type="submit">
          Process Payment
        </Button>
      </div>
    </Form>
  );
};

export default PaymentForm;
