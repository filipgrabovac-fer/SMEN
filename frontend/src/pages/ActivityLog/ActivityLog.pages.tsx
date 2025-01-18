import { Table } from "antd";
import { useGetActivityLog } from "./hooks/useGetActivityLog.hook";

const columns = [
  {
    title: "activity",
    dataIndex: "activity",
    key: "activity",
  },
  {
    title: "descritpion",
    dataIndex: "descritpion",
    key: "descritpion",
  },
  {
    title: "userId",
    dataIndex: "userId",
    key: "userId",
  },
];

export const ActivityLogOverview = () => {
  const { data } = useGetActivityLog({});
  const dataSource = data?.map((activityLog) => ({
    key: activityLog.id,
    description: activityLog.description,
    activity: activityLog.activity,
    userId: activityLog.userId,
  }));

  return (
    <div className="w-4/5 m-auto flex flex-col gap-y-6 mt-6">
      <div className="flex justify-between">
        <h1 className="text-2xl font-medium">Activity log</h1>
      </div>
      <Table columns={columns} dataSource={dataSource} />
    </div>
  );
};
