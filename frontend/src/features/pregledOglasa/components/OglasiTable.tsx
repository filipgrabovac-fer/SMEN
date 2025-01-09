import { Table } from "antd";
import type { TableProps } from "antd";
import OglasDetails from "./OglasDetails";
import { Oglas } from "../typings/oglas";

const OglasiTable = ({ data }: { data: Oglas[] }) => {
  const columns: TableProps<Oglas>["columns"] = [
    {
      title: "Naslov",
      dataIndex: "naslovOglasa",
      key: "naslovOglasa",
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
      render: (datum: Date) => datum.toLocaleDateString(),
    },
    {
      title: "Detalji",
      dataIndex: "details",
      key: "details",
      render: () => <OglasDetails />,
    },
  ];

  return <Table<Oglas> columns={columns} dataSource={data} />;
};

export default OglasiTable;
