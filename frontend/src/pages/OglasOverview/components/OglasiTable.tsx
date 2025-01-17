import { Table } from "antd";
import type { TableProps } from "antd";
import OglasDetails from "./OglasDetails";
import { Oglas } from "../oglas";

const OglasiTable = ({ data }: { data: Oglas[] }) => {
  const columns: TableProps<Oglas>["columns"] = [
    {
      title: "Naslov",
      dataIndex: "naslovOglasa",
      key: "naslovOglasa",
    },
    {
      title: "Autor",
      dataIndex: "autor",
      key: "autor",
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
      render: (_: unknown, record: Oglas) => (
        <OglasDetails
          title={record.naslovOglasa}
          content={[record.opis]}
          author={record.autor}
          date={record.datum}
        />
      ),
    },
    { title: "", dataIndex: "edit", key: "edit" },
  ];

  return <Table columns={columns} dataSource={data} />;
};

export default OglasiTable;
