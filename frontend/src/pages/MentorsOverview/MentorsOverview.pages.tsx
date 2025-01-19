import { Button, Table } from "antd";
import { CheckIcon } from "@heroicons/react/24/solid";
import { useGetMentors } from "./hooks/useGetMentors.hook";
import { usePostApproveMentorRequest } from "./hooks/usePostApproveMentorRequest.hook";
import { useQueryClient } from "@tanstack/react-query";
import { usePostRejectMentorRequest } from "./hooks/usePostRejectMentorRequest.hook";
import { MinusCircleOutlined } from "@ant-design/icons";
export const MentorsOverview = () => {
  const { data } = useGetMentors();

  const queryClient = useQueryClient();

  const { mutate: postApproveMentorRequest } = usePostApproveMentorRequest({
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: ["mentorRequests"] }),
  });
  const { mutate: postRejectMentorRequest } = usePostRejectMentorRequest({
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: ["mentorRequests"] }),
  });

  const dataSource = data?.map((mentor, i) => ({
    key: i,
    ime: mentor.firstName,
    prezime: mentor.lastName,
    email: mentor.email,
    comment: mentor.comment,
    datum_prijave: mentor.createdAt.slice(0, 10),
    approve_reject: (
      <div className="flex gap-x-2">
        {mentor.status == "ACCEPTED" && (
          <p className="text-green-500">Accepted</p>
        )}
        {mentor.status == "REJECTED" && (
          <p className="text-red-500">Rejected</p>
        )}
        {mentor.status == "PENDING" && (
          <>
            <Button
              className="bg-button_border !hover:background-button_border"
              onClick={() =>
                postApproveMentorRequest({ mentorRequestId: mentor.id })
              }
            >
              <CheckIcon className="w-5 h-5 text-white" />
            </Button>
            <Button className="border-red-600">
              <MinusCircleOutlined
                color="red"
                className="text-red-600"
                onClick={() =>
                  postRejectMentorRequest({ mentorRequestId: mentor.id })
                }
              />
            </Button>
          </>
        )}
      </div>
    ),
  }));

  const columns = [
    {
      title: "ime",
      dataIndex: "ime",
      key: "ime",
    },
    {
      title: "prezime",
      dataIndex: "prezime",
      key: "prezime",
    },
    {
      title: "email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "datum prijave",
      dataIndex: "datum_prijave",
      key: "datum_prijave",
    },
    { title: "comment", dataIndex: "comment", key: "comment" },
    {
      title: "",
      dataIndex: "approve_reject",
      key: "approve_reject",
    },
  ];

  return (
    <div>
      <div className="w-4/5 flex flex-col justify-center m-auto gap-y-6 mt-6">
        <p className="text-2xl font-medium">Mentori</p>
        <Table
          columns={columns}
          dataSource={dataSource}
          className="w-full m-auto"
        />
      </div>
    </div>
  );
};
