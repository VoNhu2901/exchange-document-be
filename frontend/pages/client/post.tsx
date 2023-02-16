import { Button } from 'antd'
import React from 'react'

type Props = {}

const Post = (props: Props) => {
	return (
		<>
			<h1 className="underline text-red-500">This is Post styling by tailwincss</h1>
			<Button type="primary">Button By Antd</Button>
		</>
	)
}

export default Post
