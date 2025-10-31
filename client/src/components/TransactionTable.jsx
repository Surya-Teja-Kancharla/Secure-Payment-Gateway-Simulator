import React from "react";
import { Table } from "react-bootstrap";
import { BsReceipt } from "react-icons/bs";

const TransactionTable = ({ transactions }) => {
  return (
    <>
      <div className="d-flex align-items-center mb-2">
        <BsReceipt size={22} className="me-2 text-primary" />
        <h5 className="mb-0 text-primary">Recent Transactions</h5>
      </div>

      <Table hover responsive bordered className="align-middle">
        <thead className="table-primary">
          <tr>
            <th>Txn Ref</th>
            <th>User ID</th>
            <th>Merchant ID</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Reason</th>
            <th>Timestamp</th>
          </tr>
        </thead>
        <tbody>
          {transactions.length > 0 ? (
            transactions.map((txn) => (
              <tr key={txn.id}>
                <td>{txn.transactionRef}</td>
                <td>{txn.userId}</td>
                <td>{txn.merchantId}</td>
                <td>₹{txn.amount.toFixed(2)}</td>
                <td
                  style={{
                    color: txn.status === "AUTHORIZED" ? "green" : "red",
                    fontWeight: "bold",
                  }}
                >
                  {txn.status}
                </td>
                <td>{txn.reason || "-"}</td>
                <td>{new Date(txn.timestamp).toLocaleString()}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="7" className="text-center text-muted">
                No transactions found
              </td>
            </tr>
          )}
        </tbody>
      </Table>
    </>
  );
};

export default TransactionTable;
