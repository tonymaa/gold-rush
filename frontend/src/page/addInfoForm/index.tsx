import React from 'react';
import { Button, Form, Select, Space, InputNumber } from 'antd';

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

    const onFinish = (values: any) => {
        console.log(values);
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
                    allowClear
                >
                    <Option value="tony">Tony</Option>
                    <Option value="jeff">Jeff</Option>
                </Select>
            </Form.Item>
            {/*<Form.Item name="email" label="Email" rules={[{ required: true }]}>*/}
            {/*  <Input style={{width: '100%'}} />*/}
            {/*</Form.Item>*/}
            <Form.Item name="expectedProfit" label="Expected profit" rules={[{ required: true }]}>
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
