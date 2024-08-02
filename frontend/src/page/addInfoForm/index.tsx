import React from 'react';
import { Button, Form, Select, Space, InputNumber } from 'antd';
import axios from "axios";

const { Option } = Select;

const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
};

const tailLayout = {
    wrapperCol: { offset: 8, span: 16 },
};


const AddInfoForm: React.FC = () => {
    const [form] = Form.useForm();

    // 将用户注册到后端进行邮件提醒服务
    const onFinish = (values: any) => {
        console.log(values);
        form.validateFields()
            .then(() => {
                axios.get('http://localhost:580/api/v1/gold/price')
                    .then(({ data }) => console.log(data.data))
            })
            .catch(e => alert(e))
    };

    const onReset = () => {
        form.resetFields();
    };

    return (
        <Form
            {...layout}
            form={form}
            name="control-hooks"
            onFinish={onFinish}
            style={{ maxWidth: 600, minWidth: 600 }}
        >
            <Form.Item name="name" label="Name" rules={[{ required: true }]}>
                <Select
                    placeholder="Select a name"
                >
                    <Option value="tony">Tony</Option>
                    <Option value="jeff">Jeff</Option>
                </Select>
            </Form.Item>
            <Form.Item name="expectedProfit" label="Expected profit" rules={[{ required: true }]}>
                <InputNumber style={{ width: '100%' }}/>
            </Form.Item>
            <Form.Item name="expectedThreshold" label="Expected threshold" rules={[{ required: true }]}>
                <InputNumber style={{ width: '100%' }}/>
            </Form.Item>
            <Form.Item {...tailLayout}>
                <Space>
                    <Button type="primary" htmlType="submit">
                        Submit
                    </Button>
                    <Button htmlType="button" onClick={onReset}>
                        Reset
                    </Button>
                </Space>
            </Form.Item>
        </Form>
    );
};

export default AddInfoForm;
