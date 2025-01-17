import { useState } from "react";
import { useNavigate } from "@tanstack/react-router";
import { homeRoute } from "../../routes/home/home.routes";
import { Button, Form, Input, Select } from "antd";
import { loginRoute } from "../../routes/auth/login.routes";
import { usePostRegister } from "./hooks/usePostRegister.hook";

export type HandleSubmitProps = {
  username: string;
  password: string;
  email: string;
  firstName: string;
  lastName: string;
  roleId: number;
};

export const Register = () => {
  const [username, setUsername] = useState<string>();
  const [email, setEmail] = useState<string>();
  const [firstName, setFirstName] = useState<string>();
  const [lastName, setLastName] = useState<string>();
  const [password, setPassword] = useState<string>();

  const { mutate: postRegister } = usePostRegister({
    onSuccess: () => {
      navigate({ to: homeRoute.fullPath });
    },
  });
  const [form] = Form.useForm();

  const navigate = useNavigate();

  return (
    <div className="flex flex-col justify-center w-screen h-screen p-4 ">
      <div className="flex flex-col items-center gap-y-4">
        <p className="text-2xl "> Registracija</p>
        <div className="bg-gray-100 w-max p-4 m-auto rounded-md">
          <div className="flex flex-col w-[300px] m-auto ">
            <Form form={form}>
              <Form.Item required name="username">
                <Input
                  name="username"
                  placeholder="korisničko ime"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                />
              </Form.Item>

              <Form.Item required name="firstName">
                <Input
                  name="firstName"
                  placeholder="ime"
                  minLength={3}
                  value={firstName}
                  onChange={(e) => setFirstName(e.target.value)}
                />
              </Form.Item>

              <Form.Item required name="lastName">
                <Input
                  name="lastName"
                  placeholder="prezime"
                  minLength={8}
                  value={lastName}
                  onChange={(e) => setLastName(e.target.value)}
                />
              </Form.Item>

              <Form.Item required name="email">
                <Input
                  name="email"
                  placeholder="email"
                  minLength={8}
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </Form.Item>

              <Form.Item required name="password">
                <Input
                  placeholder="lozinka"
                  typeof="password"
                  name="password"
                  value={password}
                  type="password"
                  minLength={8}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </Form.Item>
              <Form.Item required name="roleId">
                <Select
                  placeholder="odaberi ulogu"
                  defaultActiveFirstOption
                  options={[
                    { label: "Korisnik", value: "4" },
                    { label: "Mentor", value: "3" },
                    { label: "Vođa tima", value: "2" },
                  ]}
                />
              </Form.Item>
            </Form>
            <Button
              className="m-auto"
              onClick={() => {
                form.validateFields().then((values) =>
                  postRegister({
                    email: values.email,
                    firstName: values.firstName,
                    lastName: values.lastName,
                    password: values.password,
                    username: values.username,
                    roleId: values.roleId,
                  })
                );
              }}
            >
              Registracija
            </Button>
          </div>
        </div>
        <div className="flex gap-x-2">
          <p>Imaš račun? </p>
          <p
            className="underline text-button_border cursor-pointer"
            onClick={() => navigate({ to: loginRoute.fullPath })}
          >
            Prijavi se
          </p>
        </div>
      </div>
    </div>
  );
};
