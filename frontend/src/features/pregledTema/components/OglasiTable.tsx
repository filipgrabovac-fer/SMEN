import React from "react";
import { Table } from "antd";
import type { TableProps } from "antd";

interface DataType {
  key: string;
  name: string;
  opis: string;
  datum: Date;
  imeRadionice: string;
  link: string;
}

const columns: TableProps<DataType>["columns"] = [
  {
    title: "Ime Radionice",
    dataIndex: "imeRadionice",
    key: "imeRadionice",
  },
  {
    title: "Name",
    dataIndex: "name",
    key: "name",
  },
  {
    title: "Opis",
    dataIndex: "opis",
    key: "opis",
  },
  {
    title: "Datum",
    dataIndex: "datum",
    key: "datum",
    render: (datum: Date) => datum.toLocaleDateString(), // Format the date
  },
  {
    title: "Detalji",
    dataIndex: "link",
    key: "link",
    render: (link: string) => (
      <a href={link} target="_blank" rel="noopener noreferrer">
        View Details
      </a>
    ),
  },
];

const data: DataType[] = [
  {
    key: "1",
    name: "John Brown",
    opis: "Introduction to programming",
    datum: new Date("2023-12-01"),
    imeRadionice: "Beginner Workshop",
    link: "https://example.com/john-workshop",
  },
  {
    key: "2",
    name: "Jane Doe",
    opis: "Advanced design techniques",
    datum: new Date("2023-11-15"),
    imeRadionice: "Advanced Design Workshop",
    link: "https://example.com/jane-workshop",
  },
  {
    key: "3",
    name: "Alice Smith",
    opis: "Team collaboration strategies",
    datum: new Date("2023-10-20"),
    imeRadionice: "Collaboration Workshop",
    link: "https://example.com/alice-workshop",
  },
];

const App: React.FC = () => (
  <Table<DataType> columns={columns} dataSource={data} />
);

export default App;
