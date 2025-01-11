import { Table } from "antd";
import type { TableProps } from "antd";
import { Tema } from "../typings/tema";

const TemaTable = ({ data }: { data: Tema[] }) => {
  const columns: TableProps<Tema>["columns"] = [
    {
      title: "Naslov",
      dataIndex: "title",
      key: "title",
    },
    {
      title: "Description",
      dataIndex: "description",
      key: "description",
    },
    {
      title: "Opis",
      dataIndex: "tags",
      key: "tags",
      render: (tags: string[]) => tags.join(", "),
    },
    {
      title: "Radionice",
    },
  ];

  return <Table<Tema> columns={columns} dataSource={data} />;
};

export default TemaTable;
